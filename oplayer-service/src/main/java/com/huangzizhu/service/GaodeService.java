package com.huangzizhu.service;


import com.huangzizhu.pojo.IpResponse;
import com.huangzizhu.pojo.WeatherInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class GaodeService {

    private final WebClient webClient = WebClient.create();
    @Value("${gaode.key}") private String key;

    public IpResponse getIpInfo(String ip){
        String url = "https://restapi.amap.com/v3/ip?key=" + key + "&ip=" + ip;
        return   this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(IpResponse.class)
                .block();
    }
    public WeatherInfo getWeatherInfo(String ip){
        IpResponse ipResponse = getIpInfo(ip);
        String adcode = ipResponse.getAdcode();
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=" + key + "&city=" + adcode;
        return   this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherInfo.class)
                .block();
    }


}
