/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demoapp.attendance;

/**
 *
 * @author Dell
 */

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;
import javafx.scene.control.Label;

public class Sensor extends Label {
    
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    
    public Sensor(){
        init();
    }
    
    public void init(){
        capturer.addDataListener(new DPFPDataAdapter() {
			@Override public void dataAcquired(final DPFPDataEvent e) {
                                setText("The fingerprint sample was captured.");
                                setText("Scan the same fingerprint again.");
				
			}
		});
		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override public void readerConnected(final DPFPReaderStatusEvent e) {
				setText("The fingerprint reader was connected.");
			}
			@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
				setText("The fingerprint reader was disconnected.");
			}
		});
    }
    
    public void process(DPFPSample sample){
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        if (features != null) try
		{
			enroller.addFeatures(features);		// Add feature set to template.
		}
		catch (DPFPImageQualityException ex) { }
		finally {
			//updateStatus();

			// Check if template has been created.
			switch(enroller.getTemplateStatus())
			{
				case TEMPLATE_STATUS_READY:	// report success and stop capturing
					stop();
					//((MainForm)getOwner()).setTemplate(enroller.getTemplate());
					//setPrompt("Click Close, and then click Fingerprint Verification.");
					break;

				case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
					enroller.clear();
					stop();
					//updateStatus();
					//((MainForm)getOwner()).setTemplate(null);
					//JOptionPane.showMessageDialog(EnrollmentForm.this, "The fingerprint template is not valid. Repeat fingerprint enrollment.", "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
					//start();
					break;
			}
		}
    }
    
    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
    
    protected void start()
	{
		capturer.startCapture();
		//setPrompt("Using the fingerprint reader, scan your fingerprint.");
	}

	protected void stop()
	{
		capturer.stopCapture();
	}
    
}
