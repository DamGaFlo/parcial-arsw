package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidAggregateController {
    @Autowired
    @Qualifier("ICovidAggregateServiceImp")
    ICovidAggregateService covidAggregateService;

    //TODO: Implemente todos los metodos POST que hacen falta.

    
    
    
    
    @RequestMapping(value = "/covid/result/true-positive",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<?> addAccountTP(@RequestBody Result result){
        try {
            
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/covid/result/true-negative",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<?> addAccountTN(@RequestBody Result result){
        try {
            
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/covid/result/false-positive",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<?> addAccountFP(@RequestBody Result result){
        try {
            
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/covid/result/false-negative",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<?> addAccountFN(@RequestBody Result result){
        try {
            
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    //TODO: Implemente todos los metodos GET que hacen falta.

    
    @RequestMapping( value = "/covid/result/true-positive", method = RequestMethod.GET)
    public ResponseEntity<?> offendingAccountsPositive() {
       try {

           return new ResponseEntity<>(covidAggregateService.getAllResultsByType(ResultType.TRUE_POSITIVE), HttpStatus.ACCEPTED);
       }catch (Exception ex) {
           Logger.getLogger( CovidAggregateController.class.getName() ).log( Level.SEVERE, null, ex );
           return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
       }
    }
    
    @RequestMapping( value = "/covid/result/true-negative", method = RequestMethod.GET)
    public ResponseEntity<?> offendingAccountsNegative() {
       try {

           return new ResponseEntity<>(covidAggregateService.getAllResultsByType(ResultType.TRUE_NEGATIVE), HttpStatus.ACCEPTED);
       }catch (Exception ex) {
           Logger.getLogger( CovidAggregateController.class.getName() ).log( Level.SEVERE, null, ex );
           return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
       }
    }
    
    @RequestMapping( value = "/covid/result/false-positive", method = RequestMethod.GET)
    public ResponseEntity<?> offendingAccountsFalsePositive() {
       try {

           return new ResponseEntity<>(covidAggregateService.getAllResultsByType(ResultType.FALSE_POSITIVE), HttpStatus.ACCEPTED);
       }catch (Exception ex) {
           Logger.getLogger( CovidAggregateController.class.getName() ).log( Level.SEVERE, null, ex );
           return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
       }
    }
    
    @RequestMapping( value = "/covid/result/false-negative", method = RequestMethod.GET)
    public ResponseEntity<?> offendingAccountsFalseNegative() {
       try {

           return new ResponseEntity<>(covidAggregateService.getAllResultsByType(ResultType.FALSE_NEGATIVE), HttpStatus.ACCEPTED);
       }catch (Exception ex) {
           Logger.getLogger( CovidAggregateController.class.getName() ).log( Level.SEVERE, null, ex );
           return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
       }
    }


    //TODO: Implemente el método.

    @RequestMapping(value = "/covid/result/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests() {
        //TODO
        covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return null;
    }
    
    @RequestMapping( value = "/covid/result/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> offendingAccountsDate(@PathVariable ("date") String date) {
       try {

           return new ResponseEntity<>(covidAggregateService.getAllResultsDate(date), HttpStatus.ACCEPTED);
       }catch (Exception ex) {
           Logger.getLogger( CovidAggregateController.class.getName() ).log( Level.SEVERE, null, ex );
           return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
       }
    }
    
    
    
}