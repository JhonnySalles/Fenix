package servidor.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import javafx.beans.property.SimpleBooleanProperty;

@Converter
public class BooleanPropertyConverter implements AttributeConverter<SimpleBooleanProperty, Boolean> {

	@Override
	public Boolean convertToDatabaseColumn(SimpleBooleanProperty attribute) {
		return attribute.getValue();
	}

	@Override
	public SimpleBooleanProperty convertToEntityAttribute(Boolean dbData) {
		return new SimpleBooleanProperty(dbData);
	}
	
	

}
