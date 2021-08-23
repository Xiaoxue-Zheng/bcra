package uk.ac.herc.bcra.security;

import uk.ac.herc.bcra.domain.User;

import java.util.Arrays;
import java.util.Set;

/**
 * Constants for Spring Security authorities.
 */
public class RoleManager {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String PARTICIPANT = "ROLE_PARTICIPANT";

    public static boolean isParticipant(String authority){
        return PARTICIPANT.equals(authority);
    }

    public static boolean isAdminUser(String authority){
        return MANAGER.equals(authority) || ADMIN.equals(authority) || USER.equals(authority);
    }

    public static boolean isAdminUser(User user) {
        return user.getAuthorities().stream().anyMatch(it->isAdminUser(it.getName()));
    }

    public static boolean requireTwoFactorAuth(Set<String> authorities){
        return authorities.stream().anyMatch(it->MANAGER.equals(it) || ADMIN.equals(it));
    }

    public static String buildHierarchy(){
        String adminAndUser = String.format("%s > %s", ADMIN, USER );
        String managerAndUser = String.format("%s > %s", MANAGER, USER );
        String managerAndAdmin = String.format("%s > %s", ADMIN, MANAGER );
        String hierarchy = String.join(" and ", Arrays.asList(adminAndUser, managerAndUser, managerAndAdmin));
        return hierarchy;
    }
}
