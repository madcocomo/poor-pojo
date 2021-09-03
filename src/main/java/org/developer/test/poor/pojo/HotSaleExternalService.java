package org.developer.test.poor.pojo;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotSaleExternalService {
    <T> List<T> hotSaleItems(Class<T> tClass, int count);
}
