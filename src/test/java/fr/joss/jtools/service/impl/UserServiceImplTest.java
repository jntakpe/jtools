package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.User;
import fr.joss.jtools.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Test des services associés à l'entité {@link User}
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:applicationContext-test.xml")
public class UserServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void findByLoginTest() {
        User selrak = userService.findByLogin("selrak");
        assertEquals("Selrak", selrak.getLogin());
        assertEquals("Charles-Eric", selrak.getFirstName());
        assertNull(userService.findByLogin("totofaitduvelo"));
    }

    @Test
    public void loadByUsernameTest() {
        UserDetails joss = userDetailsService.loadUserByUsername("jOSS");
        assertNotNull(joss);
        assertEquals("jOSS", joss.getUsername());
        assertTrue(joss.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserDetails jujupiwi = userDetailsService.loadUserByUsername("jujupiwi");
        assertNotNull(jujupiwi);
        assertEquals("JujuPiwi", jujupiwi.getUsername());
        assertTrue(jujupiwi.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        User jossUser = userService.findByLogin("jOSS");
        assertEquals(Calendar.getInstance().getTime().toString(), jossUser.getLastAccess().toString());
    }

    @Test
    public void saveTest() {
        User david = new User();
        david.setLogin("Dada");
        david.setFirstName("daVId");
        david.setLastName("TreVISiol");
        david.setPassword("lolilol");
        david.setEmail("david.TREVISIOL@sopragroup.com");
        david.setBirthdate(new DateTime(1976, 1, 1, 1, 1).toDate());
        david.setPhone("11111");
        User managed = userService.save(david);
        assertEquals(managed, userService.findByLogin("dada"));
        assertEquals("David", managed.getFirstName());
        assertEquals("TREVISIOL", managed.getLastName());
        assertEquals("david.trevisiol@sopragroup.com", managed.getEmail());
        assertEquals("ROLE_USER", managed.getRole().name());
        User juju = userService.findByLogin("JujuPiwi");
        juju.setLogin("juju");
        assertNotNull(userService.findByLogin("JUJU"));
    }

    @Test
    public void isLoginAvaillableTest() {
        Long jujuId = userService.findByLogin("jujupiwi").getId();
        assertTrue(userService.isLoginAvaillable(jujuId, "JujuPiwi"));
        assertTrue(userService.isLoginAvaillable(jujuId, "JUJUPIWI"));
        assertFalse(userService.isLoginAvaillable(9999L, "JujuPiwi"));
        assertFalse(userService.isLoginAvaillable(9999L, "JUJUPIWI"));
        assertFalse(userService.isLoginAvaillable(null, "JujuPiwi"));
        assertFalse(userService.isLoginAvaillable(null, "JUJUPIWI"));
        assertTrue(userService.isLoginAvaillable(null, "totopartauski"));
        assertTrue(userService.isLoginAvaillable(jujuId, "totopartauski"));
        assertFalse(userService.isLoginAvaillable(jujuId, "jOSS"));
    }

    @Test
    public void isEmailAvaillableTest() {
        Long jujuId = userService.findByLogin("jujupiwi").getId();
        assertTrue(userService.isEmailAvaillable(jujuId, "JULIEN.GUERRIN@sopragroup.com"));
        assertTrue(userService.isEmailAvaillable(jujuId, "julien.guerrin@sopragroup.com"));
        assertFalse(userService.isEmailAvaillable(9999L, "JULIEN.GUERRIN@sopragroup.com"));
        assertFalse(userService.isEmailAvaillable(9999L, "julien.guerrin@sopragroup.com"));
        assertFalse(userService.isEmailAvaillable(null, "julien.guerrin@sopragroup.com"));
        assertFalse(userService.isEmailAvaillable(null, "JULIEN.GUERRIN@sopragroup.com"));
        assertTrue(userService.isEmailAvaillable(null, "totopartauski@sopragroup.com"));
        assertTrue(userService.isEmailAvaillable(jujuId, "totopartauski@sopragroup.com"));
        assertFalse(userService.isEmailAvaillable(jujuId, "jocelyn.ntakpe@sopragroup.com"));
    }

    @Test
    public void isPhoneAvaillableTest() {
        Long jossId = userService.findByLogin("jOSS").getId();
        assertTrue(userService.isPhoneAvaillable(jossId, "51394"));
        assertFalse(userService.isPhoneAvaillable(9999L, "51394"));
        assertFalse(userService.isPhoneAvaillable(null, "51394"));
        assertTrue(userService.isPhoneAvaillable(jossId, "99999"));
        assertFalse(userService.isPhoneAvaillable(jossId, userService.findByLogin("Selrak").getPhone()));
    }

    @Test
    public void deleteTest() {
        long nbUsers = userService.count();
        User joss = userService.findByLogin("jOSS");
        assertNotNull(joss);
        userService.delete(joss);
        assertEquals(nbUsers - 1, userService.count());
        assertNull(userService.findByLogin("jOSS"));
    }
}
