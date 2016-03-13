package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class UserUtil {

    public static final List<User> USER_LIST = Arrays.asList(
            new User(null, "Derick Rotter", "stone@meekness.com", "alfieri", 2500, true, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Vicente Wilkey", "ca-tech@dps.centrin.net.id", "topsail", 2000, true, new HashSet<>(Collections.singletonList(Role.ROLE_ADMIN))),
            new User(null, "Jackson Rispoli", "trinanda_lestyowati@telkomsel.co.id", "sweatiest", 2350, false, new HashSet<>(Collections.singletonList(Role.ROLE_ADMIN))),
            new User(null, "Barney Cleaves", "asst_dos@astonrasuna.com", "nubbly", 2000, true, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Gaylord Clore", "amartabali@dps.centrin.net.id", "pretender", 1800, true, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Sterling Varano", "achatv@cbn.net.id", "annamite", 2000, true, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Santos Ballantyne", "bali@tuguhotels.com", "ethicise", 1900, false, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Aurelio Lage", "baliminimalist@yahoo.com", "noncooperator", 1600, true, new HashSet<>(Collections.singletonList(Role.ROLE_USER))),
            new User(null, "Ali Barros", "bliss@thebale.com", "unportionable", 2000, true, new HashSet<>(Collections.singletonList(Role.ROLE_ADMIN))),
            new User(null, "Reinaldo Boggan", "adhidharma@denpasar.wasantara.net.id", "jute", 2100, false, new HashSet<>(Collections.singletonList(Role.ROLE_USER)))
    );
}
