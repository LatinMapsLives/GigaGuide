package ru.rogotovskiy.auth.service.unit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.rogotovskiy.auth.entity.Role;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.service.JwtService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplUnitTest {

    private JwtService jwtService;

    private final String secret = "testSecretKeyWithMinimum32CharДействиеersLength123";
    private final Duration lifetime = Duration.ofHours(1);

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "lifetime", lifetime);
    }

    @Test
    void generateToken_ShouldReturnValidToken_WhenUserProvided() {
        // Дано
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(List.of(role));

        // Действие
        String token = jwtService.generateToken(user);

        // Проверка
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(1, jwtService.getId(token));
        assertEquals("testuser", jwtService.getUsername(token));
        assertTrue(jwtService.getRoles(token).contains("ROLE_USER"));
    }

    @Test
    void getId_ShouldReturnCorrectUserId_FromValidToken() {
        // Дано
        String subject = "123";
        String token = createTestToken(subject, "testuser", List.of("ROLE_USER"));

        // Действие
        Integer userId = jwtService.getId(token);

        // Проверка
        assertEquals(123, userId);
    }

    @Test
    void getRoles_ShouldReturnCorrectRoles_FromValidToken() {
        // Дано
        String token = createTestToken("1", "testuser", List.of("ROLE_USER", "ROLE_ADMIN"));

        // Действие
        List<String> roles = jwtService.getRoles(token);

        // Проверка
        assertEquals(2, roles.size());
        assertTrue(roles.contains("ROLE_USER"));
        assertTrue(roles.contains("ROLE_ADMIN"));
    }

    @Test
    void getUsername_ShouldReturnCorrectUsername_FromValidToken() {
        // Дано
        String token = createTestToken("1", "johndoe", List.of("ROLE_USER"));

        // Действие
        String username = jwtService.getUsername(token);

        // Проверка
        assertEquals("johndoe", username);
    }

    @Test
    void getAllClaimsFromToken_ShouldReturnClaims_WhenTokenValid() {
        // Дано
        String token = createTestToken("42", "test", List.of("ROLE_TEST"));

        // Действие
        Claims claims = jwtService.getAllClaimsFromToken(token);

        // Проверка
        assertNotNull(claims);
        assertEquals("42", claims.getSubject());
        assertEquals("test", claims.get("username"));
        assertTrue(((List<?>) claims.get("roles")).contains("ROLE_TEST"));
    }

    @Test
    void getSigningKey_ShouldReturnValidKey() {
        // Действие
        SecretKey key = jwtService.getSigningKey();

        // Проверка
        assertNotNull(key);
        assertEquals("HmacSHA256", key.getAlgorithm());
        assertArrayEquals(secret.getBytes(StandardCharsets.UTF_8), key.getEncoded());
    }

    @Test
    void generatedToken_ShouldContainExpirationDate() {
        // Дано
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setRoles(List.of());

        // Действие
        String token = jwtService.generateToken(user);
        Claims claims = jwtService.getAllClaimsFromToken(token);

        // Проверка
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    private String createTestToken(String subject, String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("username", username);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + lifetime.toMillis()))
                .signWith(jwtService.getSigningKey())
                .compact();
    }
}