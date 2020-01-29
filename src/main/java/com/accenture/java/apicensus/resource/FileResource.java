package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoint related to files.
 *
 * @author Gian F. S.
 */
@Component
public class FileResource extends ExceptionCatcherResource {

    /**
     * Works like Observer-Observable pattern, if a new file is added
     * inside the folder, it will be processed.
     *
     * With the attribute delete in true, at the end, the file will
     * be deleted. And antInclude determinate the naming convention
     * of the file.
     *
     * @exception Exception the exception
     */
    @Override
    public void configure() throws Exception {
        super.configure();

        from(Endpoint.FILE_PERSON_FTP).routeId(RouteID.FILE_PERSON_FTP.id())
            .to(Endpoint.DIRECT_DO_INSERT_PEOPLE);
    }
}
