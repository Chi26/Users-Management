package com.shopping.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopping.common.entity.User;

public class UserCSVExporter {

	public void export(List<User> allUsers, HttpServletResponse response) throws IOException {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timeStamp = simpleDateFormat.format(new Date());
		String fileName = "Users_" + timeStamp + ".csv";
		response.setContentType("text/csv");
		String headerKey = "Content-Disposition";
		String headerValue = "attchment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);

		CsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled" };
		String[] csvContents = { "id", "email", "firstName", "lastName", "roles", "enabled" };
		csvBeanWriter.writeHeader(csvHeader);
	
		for (User user : allUsers) {
			csvBeanWriter.write(user, csvContents);
		}
		
		csvBeanWriter.close();
	}
}
