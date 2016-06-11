package org.xine.xepensees.presentation.faces.messages;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

//@ApplicationScoped
public class ResourceBundleProducer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ResourceBundle bundle;
	
	@Produces @MessageBundle
    public ResourceBundle getBundle() {
	if (this.bundle == null) {
           FacesContext context = FacesContext.getCurrentInstance();
           this.bundle = context.getApplication().getResourceBundle(context, "msg");
       }
       return this.bundle;
   }

}
