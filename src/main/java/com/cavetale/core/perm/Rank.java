package com.cavetale.core.perm;

import com.cavetale.core.message.AltTextSupplier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.kyori.adventure.text.ComponentLike;

public interface Rank extends ComponentLike, AltTextSupplier {
    String getKey();

    static List<Rank> all() {
        List<Rank> all = new ArrayList<>();
        all.addAll(Arrays.asList(StaffRank.values()));
        all.addAll(Arrays.asList(ExtraRank.values()));
        return all;
    }
}
