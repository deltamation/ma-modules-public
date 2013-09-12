/*
 * Copyright (C) 2013 Deltamation Software. All rights reserved.
 * @author Jared Wiltshire
 */

package com.serotonin.m2m2.reports.web;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.swing.NaiveUserAgent;

import com.lowagie.text.Image;
import com.serotonin.m2m2.Common;

/**
 * Copyright (C) 2013 Deltamation Software. All rights reserved.
 * @author Jared Wiltshire
 */
class PdfImageResolver extends NaiveUserAgent {
    Map<String, byte[]> imageData;
    private static final int IMAGE_CACHE_CAPACITY = 32;
    private SharedContext _sharedContext;
    
    /**
     * @param outputDevice
     */
    public PdfImageResolver(Map<String, byte[]> imageData) {
        super(IMAGE_CACHE_CAPACITY);
        this.imageData = imageData;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ImageResource getImageResource(String uri) {
        uri = resolveURI(uri);
        ImageResource ir = (ImageResource) this._imageCache.get(uri);

        if (ir == null) {
            File input;
            Image image = null;
            
            try {
                input = new File(uri);

                if (imageData.containsKey(uri)) {
                    image = Image.getInstance(imageData.get(uri));
                }
                else if (input.exists()) {
                    image = Image.getInstance(FileUtils.readFileToByteArray(input));
                }
                
                if (image != null) {
                    scaleToOutputResolution(image);
                    ir = new ImageResource(uri, new ITextFSImage(image));
                    this._imageCache.put(uri, ir);
                }
            } catch (Exception e) {
            }
        }
        
        if (ir == null) {
            ir = new ImageResource(uri, null);
        }
        
        return ir;
    }
    
    @Override
    public String resolveURI(String uri) {
        if (uri == null)
            return null;
        
        if (uri.startsWith("reportImageChart/")) {
            // chop reportImageChart/ off the start
            return uri.substring(17);
        }
        
        uri = "/web" + (uri.startsWith("/") ? uri : "/" + uri);
        
        File location = new File(Common.MA_HOME + "/overrides" + uri);
        if (!location.exists()) {
            location = new File(Common.MA_HOME + uri);
        }
        if (!location.exists()) {
            return null;
        }
        
        return location.getAbsolutePath();
    }
    
    private void scaleToOutputResolution(Image image) {
        float factor = this._sharedContext.getDotsPerPixel();
        image.scaleAbsolute(image.getPlainWidth() * factor,
                image.getPlainHeight() * factor);
    }

    public SharedContext getSharedContext() {
        return this._sharedContext;
    }

    public void setSharedContext(SharedContext sharedContext) {
        this._sharedContext = sharedContext;
    }
}
