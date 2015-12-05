package org.adorsys.adcore.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public abstract class CoreAbstIdentifiedEndpoint<E extends CoreAbstIdentifObject> extends CoreAbstIdentifReadEndpoint<E>{

	protected abstract CoreAbstIdentifiedEJB<E> getEjb();
	
	@POST
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E create(E entity) {
		return detach(getEjb().create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		E deleted = getEjb().deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public E update(E entity, @PathParam("id") String id) {
		// TODO: verify id integrity.
		return detach(getEjb().update(entity));
	}


	@POST
	@Path("/upload")
	public void upload(MultipartFormDataInput input) throws ServletException, IOException, AdException {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		Set<String> keySet = uploadForm.keySet();
		List<File> uploadFiles = new ArrayList<File>();
		for (String key : keySet) {
			List<InputPart> inputParts = uploadForm.get(key);
			for (InputPart inputPart : inputParts) {
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				File file = File.createTempFile(UUID.randomUUID().toString(), "_" + key); 
				FileUtils.copyInputStreamToFile(inputStream, file);
				// close input part.
				IOUtils.closeQuietly(inputStream);
				uploadFiles.add(file);
			}
			
		}
		input.close();
		
		CoreAbstLoaderRegistration loaderRegistration = getLoaderRegistration();
		if(loaderRegistration!=null)
			loaderRegistration.execute(uploadFiles);
	}
	
	protected CoreAbstLoaderRegistration getLoaderRegistration(){
		return null;
	}
}