package il.elpisor.lodging.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {
    private Set<GroupData> delegate;

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    public Groups(Groups groups) {
        this.delegate = new HashSet<GroupData>(groups.delegate);
    }

    public Groups(Collection<GroupData> groups) {
        this.delegate = new HashSet<GroupData>(groups);
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups withOut(GroupData group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }

    @Override
    protected Set delegate() {
        return this.delegate;
    }
}
