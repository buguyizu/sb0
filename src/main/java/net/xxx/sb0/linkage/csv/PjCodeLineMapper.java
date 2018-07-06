package net.xxx.sb0.linkage.csv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.LineMapper;

import net.xxx.sb0.linkage.entity.PjCode;

public class PjCodeLineMapper implements LineMapper<PjCode> {

	@Override
	public PjCode mapLine(String line, int lineNumber) throws Exception {

		String[] fields = line.replace("\"", "").split(",", 3);

		PjCode p = new PjCode();
		if (fields.length == 3) {
			p.setCode(fields[0]);

			DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			p.setStartDate(LocalDate.parse(fields[1], date_formatter));
			p.setEndDate(LocalDate.parse(fields[2], date_formatter));
		}

		return p;
	}

}
