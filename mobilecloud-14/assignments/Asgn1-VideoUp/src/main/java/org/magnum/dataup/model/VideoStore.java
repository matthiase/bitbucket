package org.magnum.dataup.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class VideoStore {
    private static final Logger logger = LoggerFactory.getLogger(VideoStore.class);
    private static final AtomicLong videoId = new AtomicLong(0L);
    private Map<Long, Video> videos = new HashMap<>();

    public Collection<Video> inventory() {
        return videos.values();
    }

    public Video get(long id) {
        Video video = videos.get(id);
        return video;
    }

    public Video put(Video video) {
        video.setId(videoId.incrementAndGet());
        video.setDataUrl(generateDataUrl(video));
        videos.put(video.getId(), video);
        logger.debug("Added video (id={}) with data url {}", video.getId(), video.getDataUrl());
        return video;
    }

    private String generateDataUrl(Video video) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String baseUrl = "http://" + request.getServerName()+ ((request.getServerPort() != 80) ? ":" +
                request.getServerPort() : "");
        return baseUrl + "/video/" + video.getId() + "/data";
    }

}
