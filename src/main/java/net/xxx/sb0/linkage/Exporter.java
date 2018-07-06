package net.xxx.sb0.linkage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import net.xxx.sb0.linkage.ds.ConTarget;
import net.xxx.sb0.linkage.entity.Work;
import net.xxx.sb0.linkage.repository.WorkRepo;

// http://www.cnblogs.com/fangwu/p/8822982.html
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableTransactionManagement
public class Exporter implements CommandLineRunner {

	@Value("${outputPath}")
	private String outputPath;

	@Autowired
	private WorkRepo workRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Importer importer;

	private static Logger logger = LogManager.getLogger(Application.class);

	@Override
	public void run(String... args) throws Exception {

//		args
		Options opts = new Options();
		opts.addOption("a", true, "aaa.");
		opts.addOption("b", false, "no need.");
		BasicParser parser = new BasicParser();

		String aValue = "";
		CommandLine cl = parser.parse(opts, args);
		if (cl.hasOption("-a")) {
			aValue = cl.getOptionValue("a");
		}
		if (cl.hasOption("-b")) {
			HelpFormatter f = new HelpFormatter();
			f.printHelp("---help--- ", opts);
			System.exit(0);
		}
//		args

		// change ds or not XXX
//		ConTarget.set(ConTarget.Third);

		importer.run("\\output\\test.csv");
		createLinkageCsv();
		logger.debug("over...");
	}

	public void createLinkageCsv() {
		jdbcTemplate.execute("truncate table work");
		
		final List<Work> workList = new ArrayList<>();
		Work w = new Work();
		w.setId(1l);
		w.setCode("C01");
		w.setHours(BigDecimal.TEN);
		workList.add(w);

		final List<Work> list = workRepo.saveAll(workList);

		exportCsv(list);
	}

	
	private void exportCsv(List<Work> list) {

		FlatFileItemWriter<Work> csvWriter = new FlatFileItemWriter<>();
		csvWriter.setResource(new FileSystemResource(outputPath));
		csvWriter.setHeaderCallback(w -> w.write("header..."));
		
		csvWriter.setLineAggregator(item -> {
			String[] fields = new String[3];
			int i = 0;
			fields[i++] = item.getCode();
			fields[i++] = "";
			fields[i++] = item.getHours().toPlainString();
			
			return StringUtils.arrayToCommaDelimitedString(fields);
		});

		try {
			csvWriter.open(new ExecutionContext());
			csvWriter.write(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			csvWriter.close();
		}
		logger.info("exported：" + outputPath);
	}
}
