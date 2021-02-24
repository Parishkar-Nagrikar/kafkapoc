package com.dmcc.kafka.msg.recovery.cotroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dmcc.kafka.msg.recovery.dto.LotMasterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class KafkaController {

	@Autowired
	private KafkaTemplate<String, LotMasterDTO> template;

	@GetMapping("/postMsgtoKafka/{name}")
	public String postMsgtoKafka(@PathVariable("name") String name) {
		String status = "success";
		List<LotMasterDTO> lotMasterDTOList = getLotMasterDTOs();

		System.out.println("+++ Before pushing the topic +++");
		for (Iterator iterator = lotMasterDTOList.iterator(); iterator.hasNext();) {
			LotMasterDTO lotMasterDTO = (LotMasterDTO) iterator.next();
			template.send("OriginalTopic", lotMasterDTO);
		}

		System.out.println("+++ After pushing the topic +++");
		return status;
	}

	public List<LotMasterDTO> getLotMasterDTOs() {
		List<LotMasterDTO> lotMasterDTOList = new ArrayList<LotMasterDTO>();

		try {
			for (int i = 0; i < 5; i++) {
				int id = (int) (new Date()).getTime();
				Thread.sleep(10);
				System.out.println("id:::" + id);
				if (i % 2 == 0) {
					String json = "{\"lotId\":" + id
							+ ",\"lotStatus\":\"Created\",\"lotVersion\":1,\"lotCreationType\":\"Auto\",\"totalQuality\":\"Band-I\",\"totalQuantity\":\"20\",\"variety\":7}";
					lotMasterDTOList.add(new ObjectMapper().readValue(json, LotMasterDTO.class));
				} else {
					String json = "{\"lotId\":" + id
							+ ",\"lotStatus\":\"Created\",\"lotVersion\":1,\"lotCreationType\":\"Auto\",\"totalQuality\":\"Band-I\",\"totalQuantity\":\"20\",\"variety\":null}";
					lotMasterDTOList.add(new ObjectMapper().readValue(json, LotMasterDTO.class));
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lotMasterDTOList;
	}
}
