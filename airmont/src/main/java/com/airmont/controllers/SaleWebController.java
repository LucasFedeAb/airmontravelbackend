package com.airmont.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.dto.SaleWebDTO;
import com.airmont.models.entity.SaleWeb;
import com.airmont.services.SaleWebService;
import com.airmont.services.SaleWebService.SalesSummary;

@RestController
@RequestMapping("/ventas-web")
public class SaleWebController {
	@Autowired
	private SaleWebService saleWebService;
 
    // Obtener todas las ventas web
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<SaleWeb>> getAllSalesWeb() {
         try {
 			List<SaleWeb> salesWeb = saleWebService.getAllSalesWeb();
 			return new ResponseEntity<>(salesWeb, HttpStatus.OK); // Codigo 200

 		} catch (Exception e) {
 			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
 		}
    }
    
    // Obtener venta web segun id
    @GetMapping(value = "/{id}/vta-web", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity <SaleWeb> getSaleWebById(@PathVariable("id") Integer id) {
    	try {
    		SaleWeb saleWeb = saleWebService.getSaleWebById(id);
			if (saleWeb != null) {
				return new ResponseEntity<>(saleWeb, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(saleWeb, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
    }
    
    // Obtener ventas web segun dni
    @GetMapping(value = "/cliente/{dni}/vta-web", produces = { MediaType.APPLICATION_JSON_VALUE })
    //@Transactional
    public ResponseEntity <List<SaleWeb>> getSalesByClientDni(@PathVariable("dni") Integer dni) {
    	try {
    		List<SaleWeb> salesWeb = saleWebService.getSalesWebByClientDni(dni);
			if (salesWeb != null) {
				return new ResponseEntity<>(salesWeb, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(salesWeb, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
    }
    
 // Obtener venta web según código
    @GetMapping(value = "/code/{codeSale}/vta-web", produces = { MediaType.APPLICATION_JSON_VALUE })
    //@Transactional
    public ResponseEntity <SaleWeb> getSaleByCodeSale(@PathVariable("codeSale") String codeSale) {
    	try {
    		SaleWeb saleWeb = saleWebService.getSaleWebByCodeSale(codeSale);
			if (saleWeb != null) {
				return new ResponseEntity<>(saleWeb, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(saleWeb, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
    }
    
 // Crear nueva venta web
    @PostMapping(value = "/crear", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SaleWebDTO> createSaleWeb(@RequestBody SaleWebDTO saleWebDTO) {
        try {
            SaleWebDTO saleWebCreated = saleWebService.createSaleWeb(saleWebDTO);
            return new ResponseEntity<>(saleWebCreated, HttpStatus.CREATED); // 201 - Created
        } catch (RuntimeException e) {
        	System.out.println("Error al crear venta:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 - Bad Request
        }
    }
    
    // Eliminar venta web segun id
//    @DeleteMapping(value = "/vta-web/{id}/eliminar")
//	public ResponseEntity<Void> deleteSaleWebById(@PathVariable("id") Integer id) {
//    	try {
//    		boolean deletedSaleWeb = saleWebService.deleteSaleWebById(id);
//    			if (deletedSaleWeb) {
//    				System.out.println("Venta-web id:" + id + " eliminada con éxito");
//    				return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Codigo 204
//    			} else {
//    				System.out.println("Venta-web id:" + id + " no encontrada");
//    				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
//    			}
//    	} catch (RuntimeException e) {
//    		System.out.println("Error al eliminar la venta-web: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 - Bad Request
//    	}
//	}
    
    @DeleteMapping(value = "/vta-web/{id}/eliminar")
    public String deleteSale(@PathVariable Integer id) {
        saleWebService.deleteSale(id);
        return "Venta con ID " + id + " eliminada, y fechas de reserva actualizadas.";
    }
    
    //Eliminar venta web segun código de venta
    @DeleteMapping(value = "/cod/{codeSale}/eliminar")
    public ResponseEntity<Void> deleteSaleByCodeSale(@PathVariable("codeSale") String codeSale) {
        try {
            saleWebService.deleteSaleWebByCodeSale(codeSale);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 - No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 - Bad Request
        }
    }
    
    //DASHBOARD
    
    @GetMapping("/previous-year-summary")
    public ResponseEntity<SalesSummary> getPreviousYearSummary() {
        SalesSummary summary = saleWebService.getSalesSummaryForPreviousYear();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/current-year-summary")
    public ResponseEntity<SalesSummary> getCurrentYearSummary() {
        SalesSummary summary = saleWebService.getSalesSummaryForCurrentYear();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/last-month-summary")
    public ResponseEntity<SalesSummary> getLastMonthSummary() {
        SalesSummary summary = saleWebService.getSalesSummaryForLastMonth();
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/current-month-reservations")
    public ResponseEntity<SalesSummary> getCurrentMonthReservationsSummary() {
        SalesSummary summary = saleWebService.getReservationsSummaryForCurrentMonth();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<SaleWeb>> getReservations() {
        List<SaleWeb> reservations = saleWebService.getReservationsForCurrentAndNextTwoMonths();
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("/reservations-summary")
    public ResponseEntity<Map<String, SaleWebService.SalesSummary>> getReservationsSummaryForSixMonths() {
        Map<String, SaleWebService.SalesSummary> summaries = saleWebService.getReservationsSummaryForSixMonths();
        return ResponseEntity.ok(summaries);
    }
    
    
    @GetMapping("/pending-reservations")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getPendingReservationsForCurrentAndNextThreeMonths() {
        Map<String, List<Map<String, String>>> pendingReservations = saleWebService.getPendingReservationsForCurrentMonthAndNextThreeMonths();
        return ResponseEntity.ok(pendingReservations);
    }

}