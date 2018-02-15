package fr.smile.jahia.service;

import org.jahia.services.content.JCRNodeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Properties;
import java.util.Set;

public class NodeService {

    public static final Logger LOG = LoggerFactory.getLogger(NodeService.class);

    public void copyProperties(JCRNodeWrapper source, JCRNodeWrapper desteny) throws RepositoryException {
        Properties props = getNotInheritedProperties(source);

        for (String propertyName : props.stringPropertyNames()) {
            desteny.setProperty(propertyName, props.getProperty(propertyName));
        }
    }

    public Properties getNotInheritedProperties(JCRNodeWrapper source) throws RepositoryException {
        Properties properties = new Properties();
        Set<String> keySet = source.getPropertiesAsString().keySet();
        keySet.removeAll(source.getParent().getPropertiesAsString().keySet());

        for (String propertyName : keySet) {
            if (source.getPropertyAsString(propertyName) != null) {
                properties.setProperty(propertyName, source.getPropertyAsString(propertyName));
            }
        }
        return properties;
    }
}
