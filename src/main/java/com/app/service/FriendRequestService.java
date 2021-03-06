package com.app.service;

import com.app.model.FriendRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendRequestService {

    public Set<FriendRequest> filterRequested(Set<FriendRequest> list)
    {
        return list.stream().filter(x->x.getStatus().equals(FriendRequest.Status.REQUESTED)).collect(Collectors.toSet());
    }

    public Set<FriendRequest> filterAccepted(Set<FriendRequest> list)
    {
        return list.stream().filter(x->x.getStatus().equals(FriendRequest.Status.ACCEPTED)).collect(Collectors.toSet());
    }
}
