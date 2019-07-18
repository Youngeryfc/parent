package com.yfc.fegin;

import com.yfc.dao.MemberService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "member")
public interface MemberServiceFegin extends MemberService {
}
