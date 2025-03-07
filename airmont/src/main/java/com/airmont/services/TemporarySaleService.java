package com.airmont.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.dto.ItemSaleDTO;
import com.airmont.dto.SaleWebDTO;
import com.airmont.models.entity.SaleItem;
import com.airmont.models.entity.TemporarySale;
import com.airmont.repositories.TemporarySaleRepository;


@Service
public class TemporarySaleService {
	
	@Autowired
	TemporarySaleRepository temporarySaleRepository;
	
	@Autowired
	private SaleWebService saleWebService;
	
	// Obtener venta web temporal por numero de preferencia
    public TemporarySale getTemporarySaleByPreferenceId(String preferenceId) {
			return temporarySaleRepository.findByPreferenceId(preferenceId);
	}
    
    public void createDefinitiveSaleWeb(TemporarySale temporarySale) {
        SaleWebDTO saleWebDTO = new SaleWebDTO();
        
        // Transferir datos de venta temporal a venta definitiva
        saleWebDTO.setTotalSaleAmount(temporarySale.getTotalSaleAmount());
        saleWebDTO.setCodeDiscount(temporarySale.getCodeDiscount());
        saleWebDTO.setTypeIdentificationClient(temporarySale.getTypeIdentificationClient());
        saleWebDTO.setDniClient(temporarySale.getDniClient());
        saleWebDTO.setFirstNameClient(temporarySale.getFirstNameClient());
        saleWebDTO.setLastNameClient(temporarySale.getLastNameClient());
        saleWebDTO.setEmailClient(temporarySale.getEmailClient());
        saleWebDTO.setPhoneClient(temporarySale.getPhoneClient());
        saleWebDTO.setCountryClient(temporarySale.getCountryClient());

        // Datos de tour
        saleWebDTO.setOrderStatus(temporarySale.getOrderStatus());
        saleWebDTO.setStartingPoint(temporarySale.getStartingPoint());
        saleWebDTO.setDestinationPoints(temporarySale.getDestinationPoints());
        saleWebDTO.setImportantObservations(temporarySale.getImportantObservations());
        
        //Datos del pago
        saleWebDTO.setPaymentMethod(temporarySale.getPaymentMethod());
        saleWebDTO.setPaymentId(temporarySale.getPaymentId());
        saleWebDTO.setPaymentStatus(temporarySale.getPaymentStatus());
        saleWebDTO.setPaymentCardId(temporarySale.getPaymentCardId());
        saleWebDTO.setPaymentCardType(temporarySale.getPaymentCardType());
        saleWebDTO.setPaymentCardName(temporarySale.getPaymentCardName());
        saleWebDTO.setPaymentCardIdentification(temporarySale.getPaymentCardIdentification());
        saleWebDTO.setPaymentCardExpirationYear(temporarySale.getPaymentCardExpirationYear());
        saleWebDTO.setPaymentCardExpirationMonth(temporarySale.getPaymentCardExpirationMonth());
        saleWebDTO.setPaymentCardFirstNumbers(temporarySale.getPaymentCardFirstNumbers());
        saleWebDTO.setPaymentCardLastNumbers(temporarySale.getPaymentCardLastNumbers());
        saleWebDTO.setInstallments(temporarySale.getInstallments());
        saleWebDTO.setNetReceivedAmount(temporarySale.getNetReceivedAmount());
              

     // Convertir items de SaleItem a ItemSaleDTO
        List<ItemSaleDTO> itemSaleDTOs = convertToItemSaleDTO(temporarySale.getItems());
        saleWebDTO.setItems(itemSaleDTOs);

        // Método de pago
        saleWebDTO.setPaymentMethod(temporarySale.getPaymentMethod());

        // Crear la venta definitiva
        saleWebService.createSaleWeb(saleWebDTO);

        // Eliminar los datos temporales
        temporarySaleRepository.delete(temporarySale);
    }
    
    private List<ItemSaleDTO> convertToItemSaleDTO(List<SaleItem> saleItems) {
        return saleItems.stream().map(saleItem -> {
            ItemSaleDTO itemSaleDTO = new ItemSaleDTO();
            itemSaleDTO.setProductId(saleItem.getProductId());
            itemSaleDTO.setQuantity(saleItem.getQuantityCars());
            itemSaleDTO.setCode(saleItem.getCode());
            itemSaleDTO.setCategory(saleItem.getCategory());
            itemSaleDTO.setSubcategory(saleItem.getSubcategory());
            itemSaleDTO.setName(saleItem.getName());
            itemSaleDTO.setDate(saleItem.getReservedDate());
            itemSaleDTO.setHourInit(saleItem.getHourInit());
            
            itemSaleDTO.setEndTime(saleItem.getEndTimeTour());
            itemSaleDTO.setImage(saleItem.getImage());
            itemSaleDTO.setPromotion(saleItem.isPromotion());
            
            itemSaleDTO.setPersonsQuantity(saleItem.getPersonsQuantity());
            itemSaleDTO.setQuantityAdults(saleItem.getQuantityAdults());
            itemSaleDTO.setQuantityMinors(saleItem.getQuantityMinors());
            itemSaleDTO.setQuantityChildrens(saleItem.getQuantityChildrens());
            itemSaleDTO.setQuantityBabys(saleItem.getQuantityBabys());
            itemSaleDTO.setTypeTour(saleItem.getTypeTour());
            itemSaleDTO.setPrice(saleItem.getSalePrice());
            itemSaleDTO.setSubtotal(saleItem.getSubtotal());
            return itemSaleDTO;
        }).collect(Collectors.toList());
    }

}
