package net.xxx.sb0.linkage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.xxx.sb0.linkage.csv.PjCodeLineMapper;
import net.xxx.sb0.linkage.entity.PjCode;
import net.xxx.sb0.linkage.repository.PjCodeRepo;

@Component
public class Importer {

	@Autowired
	private PjCodeRepo pjCodeRepo;

	@Transactional
	public List<PjCode> run(String importPath) {

		FlatFileItemReader<PjCode> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(importPath));
		reader.setLineMapper(new PjCodeLineMapper());
		reader.setLinesToSkip(0);

		List<PjCode> allList = new ArrayList<>();

		try {

			reader.open(new ExecutionContext());
			PjCode cpd;
			while ((cpd = reader.read()) != null) {
				List<PjCode> list = pjCodeRepo.findByCode(cpd.getCode());

				if (list.isEmpty()) {
					allList.add(cpd);
				} else {
					PjCode entity = list.get(0);
					entity.setCode(cpd.getCode());
					entity.setStartDate(cpd.getStartDate());
					entity.setEndDate(cpd.getEndDate());

					allList.add(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			reader.close();
		}

		return pjCodeRepo.saveAll(allList);
	}
}
