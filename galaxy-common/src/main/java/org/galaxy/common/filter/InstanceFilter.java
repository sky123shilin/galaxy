package org.galaxy.common.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class InstanceFilter<T> {

    private final Collection<? extends T> includes;
    private final Collection<? extends T> excludes;
    private final boolean matchIfEmpty;

    public InstanceFilter(@Nullable Collection<? extends T> includes, @Nullable Collection<? extends T> excludes, boolean matchIfEmpty) {
        this.includes = (includes != null ? includes : Collections.emptyList());
        this.excludes = (excludes != null ? excludes : Collections.emptyList());
        this.matchIfEmpty = matchIfEmpty;
    }

    public boolean match(T instance) {
        Assert.notNull(instance, "Instance to match must not be null");
        boolean includesSet = !this.includes.isEmpty();
        boolean excludesSet = !this.excludes.isEmpty();
        if (!includesSet && !excludesSet) {
            return this.matchIfEmpty;
        } else {
            boolean matchIncludes = this.match(instance, this.includes);
            boolean matchExcludes = this.match(instance, this.excludes);
            if (!includesSet) {
                return !matchExcludes;
            } else if (!excludesSet) {
                return matchIncludes;
            } else {
                return matchIncludes && !matchExcludes;
            }
        }
    }

    protected boolean match(T instance, Object candidate) {
        return instance.equals(candidate);
    }

    protected boolean match(T instance, Collection<? extends T> candidates) {
        Iterator<? extends T> var3 = candidates.iterator();

        Object candidate;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            candidate = var3.next();
        } while(!this.match(instance, candidate));

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(": includes=").append(this.includes);
        sb.append(", excludes=").append(this.excludes);
        sb.append(", matchIfEmpty=").append(this.matchIfEmpty);
        return sb.toString();
    }
}
