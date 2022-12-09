package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		Boolean allValid = true;
        
		for(int i = 0; i < 3; i++){
			if(i == 0){
				if(!validateDataFields(car)){
					allValid = false;
					System.out.println("Data Field missing.");	
					break;
				}
			}
			if(i == 1){
				if(!validateParts(car)){
					allValid = false;
					System.out.println("Parts missing.");	
					break;
				}
			}
			if(i == 2){
				if(!validatePartsCondition(car)){
					allValid = false;
					System.out.println("Parts are in bad condition.");
					break;
				}
			}
		}
		if(allValid){
			System.out.println("Validation has succeeded! You're good to go!");	
		}
		else{
			System.out.println("Validation has failed! Your car needs more work!");	
		}
		

	}
    
	private boolean validateDataFields(Car car){
		List<Boolean> validFields = new ArrayList<Boolean>();
		validFields.add(printMissingDataField(car.getMake(), "MAKE"));
		validFields.add(printMissingDataField(car.getModel(), "MODEL"));
		validFields.add(printMissingDataField(car.getYear(), "YEAR"));
		for(Boolean field : validFields){
			if(field == false){
				return false;
			}
		}
        return true;
	}

	private boolean validateParts(Car car){
		boolean valid = true;
		for (Map.Entry<PartType, Integer> missingPart : car.getMissingPartsMap().entrySet()){
			valid = false;
			printMissingPart(missingPart.getKey(), missingPart.getValue());
		} 
        return valid;
	}

	private boolean validatePartsCondition(Car car){
		boolean valid = true;
		for(Part part : car.getParts()){
			if(!part.isInWorkingCondition()){
				valid = false;
				printDamagedPart(part.getType(), part.getCondition());
			}
		}
        return valid;
	}
	private boolean printMissingDataField(String dataField, String field) {
		if (dataField == null) {
			System.out.println(String.format("Missing Data Field Detected: %s ", field));
			return false;
		}
		else{
			return true;
		}

	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
