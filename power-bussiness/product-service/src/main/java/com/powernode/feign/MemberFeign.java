package com.powernode.feign;

import com.powernode.domain.Member;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "member-service")
public interface MemberFeign {

    @GetMapping("/p/user/getMembersByOpenIds")
    List<Member> getMembersByOpenIds(@RequestParam List<String> openIds);

}
