//package br.com.devschool.demo.application.controller;
//
//import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Collections;
//
//import br.com.devschool.demo.domain.model.internal.*;
//import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
//import br.com.devschool.demo.infra.exception.RequestPropertyNotFoundException;
//import br.com.devschool.demo.util.JsonConvertionUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//
//import br.com.devschool.demo.domain.service.impl.RequestPropertyServiceImpl;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ExtendWith(MockitoExtension.class)
//class RequestPropertyControllerTest {
//
//	private static final String REQUESTPROPERTY_API_URL_PATH = "/requestProperty";
//	private static final Integer REQUESTPROPERTY_VALID_ID = 1;
//	private static final Integer REQUESTPROPERTY_INVALID_ID = 2;
//	private RequestProperty requestPropertyBuilder() {
//		return RequestProperty.builder()
//				.id(2)
//				.request(Request.builder().id(1).build())
//				.key("key")
//				.value(1)
//				.order("order")
//				.build();
//	}
//
//	@InjectMocks
//	private RequestPropertyController controller;
//
//	@Mock
//	private RequestPropertyServiceImpl service;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	void setUp() {
//		mockMvc = MockMvcBuilders.standaloneSetup(controller)
//				.setControllerAdvice(new ErrorHandler())
//				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//				.build();
//	}
//
//	@Test
//	void requestGetForRequestPropertysMustReturnStatusOk() throws Exception {
//		RequestProperty requestProperty = requestPropertyBuilder();
//
//		when(service.getAllRequestProperty()).thenReturn(Collections.singletonList(requestProperty));
//
//		mockMvc.perform(get(REQUESTPROPERTY_API_URL_PATH)
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
//		RequestProperty requestProperty = requestPropertyBuilder();
//
//		when(service.getRequestPropertyById(REQUESTPROPERTY_VALID_ID)).thenReturn(requestProperty);
//
//		mockMvc.perform(get(REQUESTPROPERTY_API_URL_PATH + "/" + REQUESTPROPERTY_VALID_ID)
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusInternalServerError() throws Exception {
//
//		when(service.getRequestPropertyById(REQUESTPROPERTY_INVALID_ID)).thenThrow(RequestPropertyNotFoundException.class);
//
//		mockMvc.perform(get(REQUESTPROPERTY_API_URL_PATH + "/" + REQUESTPROPERTY_INVALID_ID)
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isInternalServerError());
//	}
//
//	@Test
//	void quandoPOSTForChamadoEUmaPropriedadeDeRequisicaoForCriadaRetorneStatusIsOk() throws Exception {
//
//		RequestProperty requestProperty = requestPropertyBuilder();
//		RequestPropertyDTO requestPropertyDTO = new RequestPropertyDTO(requestProperty);
//
//		when(service.createVRequestProperty(requestPropertyDTO)).thenReturn(requestProperty);
//
//		mockMvc.perform(post(REQUESTPROPERTY_API_URL_PATH)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(asJsonString(requestPropertyDTO)))
//				.andExpect(status().isOk());
//
//	}
//
//	@Test
//	void quandoPUTForChamadoEUmaPropriedadeDeRequisicaoForAtualizadaRetorneStatusIsOk() throws Exception {
//
//		RequestProperty requestProperty = requestPropertyBuilder();
//		RequestPropertyDTO requestPropertyDTO = new RequestPropertyDTO(requestProperty);
//
//		when(service.updateRequestProperty(REQUESTPROPERTY_VALID_ID, requestPropertyDTO)).thenReturn(requestProperty);
//
//		mockMvc.perform(put(REQUESTPROPERTY_API_URL_PATH + "/" + REQUESTPROPERTY_VALID_ID )
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(asJsonString(requestPropertyDTO)))
//				.andExpect(status().isOk());
//
//	}
//
//	@Test
//	void quandoDELETEForChamadoComUmIDValidoRetorneStatusIsOk() throws Exception {
//		doNothing().when(service).deleteRequestPropertyById(REQUESTPROPERTY_VALID_ID);
//
//		mockMvc.perform(delete(REQUESTPROPERTY_API_URL_PATH + "/" + REQUESTPROPERTY_VALID_ID )
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}
//
//}
