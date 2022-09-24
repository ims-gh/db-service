package com.ims.ordermanagement.config;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class OrderIdGenerator extends SequenceStyleGenerator {

    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%04d";

    public static final String DATE_NUMBER_SEPARATOR_PARAMETER = "dateNumberSeparator";
    public static final String DATE_NUMBER_SEPARATOR_DEFAULT = "-";

    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return setDate() + String.format(format, super.generate(session, object));
    }

    @Override
    public void configure(Type type, Properties params,
                          ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);

        String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
        String dateNumberSeparator = ConfigurationHelper.getString(DATE_NUMBER_SEPARATOR_PARAMETER, params, DATE_NUMBER_SEPARATOR_DEFAULT);
        this.format = dateNumberSeparator+numberFormat;
    }

    private String setDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dMMMyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date()).toUpperCase();
    }


}