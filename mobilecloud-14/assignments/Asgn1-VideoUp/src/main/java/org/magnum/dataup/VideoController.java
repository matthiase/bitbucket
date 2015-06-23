/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Controller
public class VideoController {

    private final VideoStore store = new VideoStore();
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return store.inventory();
    }

    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video video) {
        return store.put(video);
    }

    @RequestMapping(value = "/video/{id}/data", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<VideoStatus> uploadVideoData(
            @PathVariable("id") long id,
            @RequestParam("data")MultipartFile data)
    {
        Video video = store.get(id);
        if (video != null && !data.isEmpty()) {
            try (InputStream inputStream = data.getInputStream()) {
                VideoFileManager.get().saveVideoData(video, inputStream);
                return new ResponseEntity<>(new VideoStatus(VideoStatus.VideoState.READY), HttpStatus.OK);
            }
            catch (IOException | RuntimeException ex) {
                logger.error("Failed to upload video (id={}).", id, ex);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/video/{id}/data", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> downloadVideoData(
            @PathVariable("id") long id,
            HttpServletResponse response)
    {
        Video video = store.get(id);
        if (video != null) {
            try {
                VideoFileManager fileManager = VideoFileManager.get();
                if (fileManager.hasVideoData(video)) {

                    fileManager.copyVideoData(video, response.getOutputStream());
                    final HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType("video/mpeg"));
                    return new ResponseEntity<>(headers, HttpStatus.OK);
                }
            }
            catch (IOException ex) {
                logger.error("Failed to download video (id={}).", id, ex);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
