package com.example.demo.cache;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
public class HostTagCache {
    private List<Map.Entry<String,Integer>> hostTag;
}
