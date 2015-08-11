package org.adorsys.adcatal.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.upload.FlowInfo;
import org.adorsys.adcore.upload.FlowInfoStorage;
import org.adorsys.adcore.upload.HttpUtils;
import org.adorsys.adcore.upload.XlsProcessEJB;
import org.adorsys.adcore.upload.XlsType;

/**
 * upload xls file
 * @author guymoyo
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/upload")
public class UploadXlsEndpoint {

	@Inject
	private XlsProcessEJB xlsProcessEJB;
	/*
	 * default upload folder
	 */
	public static final String UPLOAD_DIR = "/tmp";
	
	@POST
	public void sendPost(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException, IOException, AdException {
		
		PrintWriter out = response.getWriter();
		
		int flowChunkNumber = getflowChunkNumber(request);

		FlowInfo info = getFlowInfo(request);

		RandomAccessFile raf = new RandomAccessFile(info.flowFilePath, "rw");

		// Seek to position
		raf.seek((flowChunkNumber - 1) * info.flowChunkSize);

		// Save to file
		InputStream is = request.getInputStream();
		long readed = 0;
		long content_length = request.getContentLength();
		byte[] bytes = new byte[1024 * 100];
		while (readed < content_length) {
			int r = is.read(bytes);
			if (r < 0) {
				break;
			}
			raf.write(bytes, 0, r);
			readed += r;
		}
		raf.close();

		// Mark as uploaded.
		info.uploadedChunks.add(new FlowInfo.flowChunkNumber(flowChunkNumber));
		String archivoFinal = info.checkIfUploadFinished();
		if (archivoFinal != null) { // Check if all chunks uploaded, and
			// change filename
			xlsProcessEJB.processXls(info);
			FlowInfoStorage.getInstance().remove(info);
			response.getWriter().print("All finished.");

		} else {
			response.getWriter().print("Upload");
		}
		// out.println(myObj.toString());

		out.close();
	}
	
	/*@GET
	public void sendGEt(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException, IOException, AdException {
		
		int flowChunkNumber = getflowChunkNumber(request);
		System.out.println("Do Get");
		
		PrintWriter out = response.getWriter();
		
		FlowInfo info = getFlowInfo(request);
		
		Object fcn = new FlowInfo.flowChunkNumber(flowChunkNumber);
		
		if (info.uploadedChunks.contains(fcn)) {
			System.out.println("Do Get arriba");
			response.getWriter().print("Uploaded."); // This Chunk has been
														// Uploaded.
		} else {
			System.out.println("Do Get something is wrong");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		out.close();
	}
*/
	private int getflowChunkNumber(HttpServletRequest request) {
		return HttpUtils.toInt(request.getParameter("flowChunkNumber"), -1);
	}

	private FlowInfo getFlowInfo(HttpServletRequest request)
			throws ServletException, AdException {

		String base_dir = UPLOAD_DIR;

		int FlowChunkSize = HttpUtils.toInt(
				request.getParameter("flowChunkSize"), -1);
		
		long FlowTotalSize = HttpUtils.toLong(
				request.getParameter("flowTotalSize"), -1); //flowTotalChunks
		String FlowIdentifier = request.getParameter("flowIdentifier");
		String FlowFilename = request.getParameter("flowFilename");
		String FlowRelativePath = request.getParameter("flowRelativePath");
		XlsType xlsType = checkXlsType(FlowFilename);
		
		FlowFilename = makeUniqueName(FlowFilename);
		
		// Here we add a ".temp" to every upload file to indicate NON-FINISHED
		String FlowFilePath = new File(base_dir, FlowFilename)
				.getAbsolutePath() + ".temp";

		FlowInfoStorage storage = FlowInfoStorage.getInstance();

		FlowInfo info = storage.get(FlowChunkSize, FlowTotalSize,
				FlowIdentifier, FlowFilename, FlowRelativePath, FlowFilePath, xlsType);
		if (!info.valid()) {
			storage.remove(info);
			throw new ServletException("Invalid request params.");
		}
		return info;
	}

	private XlsType checkXlsType(String flowFilename) throws AdException {
		// TODO throw error if it is not a xls file
		
		if(flowFilename.contains("catal"))
		return XlsType.ADCATAL;
		if(flowFilename.contains("stock"))
			return XlsType.ADSTOCK;
		if(flowFilename.contains("inv"))
			return XlsType.ADINVTRY;
		if(flowFilename.contains("pr"))
			return XlsType.ADPRCMT;
		else
			throw new AdException("rename file with either: adcatal, adstock, adinvtry, adprcmt");
	}

	private String makeUniqueName(String flowFilename) {
		String rd = UUID.randomUUID().toString();
		return rd+flowFilename;
	}

	
}