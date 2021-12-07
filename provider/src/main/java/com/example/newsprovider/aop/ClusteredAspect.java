package com.example.newsprovider.aop;

import com.hazelcast.cluster.Member;
import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Slf4j
@Component
@AllArgsConstructor
public class ClusteredAspect {

    private final Optional<HazelcastInstance> hazelcast;

    @Around("@annotation(com.example.newsprovider.annotation.Clustered)")
    public Object checkPrimaryMember(ProceedingJoinPoint jp) throws Throwable {

        Optional<Member> member = getPrimaryMember();

        if (member.isEmpty() || member.get().localMember()) {
            return jp.proceed();
        } else {
            log.debug("Method {} is launched on: {}", jp.getSignature(), member.get());
            return null;
        }
    }

    private Optional<Member> getPrimaryMember() {
        return hazelcast.map(i -> i.getCluster().getMembers().iterator().next());
    }
}
