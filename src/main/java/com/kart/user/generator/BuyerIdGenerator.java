package com.kart.user.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class BuyerIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		   String prefix = "B";
		   String suffix = "";
		   Connection connection = session.connection();

		    try {
		        Statement statement = connection.createStatement();

		        ResultSet rs = statement.executeQuery("select MAX(buyerid) AS Id From buyer;");

		        if(rs.next())
		        {
	            	String temp = rs.getString("Id").substring(1);
			        int id = Integer.parseInt(temp) + 1;
				    suffix = String.valueOf(id);
					return prefix + suffix;
	            }
		        
		    } catch (SQLException e) {
		       
		        e.printStackTrace();
		    }
		    return null;
	}

}
