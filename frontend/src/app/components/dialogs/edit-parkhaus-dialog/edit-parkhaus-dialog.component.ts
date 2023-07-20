import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { LuxDialogRef } from '@ihk-gfi/lux-components';
import { Observable, catchError, map, of } from 'rxjs';
import { ParkhausEditierenDto } from 'src/app/facade/dto/ParkhausEditierenDto';

@Component({
  selector: 'app-edit-parkhaus-dialog',
  templateUrl: './edit-parkhaus-dialog.component.html',
  styleUrls: ['./edit-parkhaus-dialog.component.scss']
})


export class EditParkhausDialogComponent implements OnInit{

  public parkhaus: ParkhausEditierenDto;
  public addresse: string;

  autocompleteOptions: any[] = [];

  constructor(public luxDialogRef: LuxDialogRef, private httpClient: HttpClient) {}

  ngOnInit(): void {
    if(this.luxDialogRef.data != null) {
      this.parkhaus = this.luxDialogRef.data;
    }
  }

  onOptionSelected(selected) {
    if(!selected) {
      return;
    }
    const placeId = selected.value;
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ placeId }, (results: any[], status: string) => {
      if (status === google.maps.GeocoderStatus.OK && results.length > 0) {
        const address = results[0].address_components;
        const streetNumber = address.find(component =>
          component.types.includes('street_number')
        )?.short_name;
        const streetName = address.find(component =>
          component.types.includes('route')
        )?.short_name;
        const city = address.find(component =>
          component.types.includes("locality") || component.types.includes("administrative_area_level_3") || component.types.includes("administrative_area_level_2") || component.types.includes("administrative_area_level_1") 
        )?.long_name;
        const postalCode = address.find(component =>
          component.types.includes('postal_code')
        )?.short_name;
  
        this.parkhaus.hausnummer = streetNumber || '';   
        this.parkhaus.strasse = streetName || '';
        this.parkhaus.ort = city || '';
        this.parkhaus.plz = postalCode || '';
      }

    });
  }

  onValueChange(event: KeyboardEvent) {
    
    const searchTerm = (event.target as HTMLInputElement).value;
    if (searchTerm) {
      const autocompleteService = new google.maps.places.AutocompleteService();
      autocompleteService.getPlacePredictions(
        { input: searchTerm, componentRestrictions: { country: 'de' }, types: ['address'] },
        (predictions: any[], status: string) => {
          if (status === google.maps.places.PlacesServiceStatus.OK) {
            this.autocompleteOptions = predictions.map(prediction => ({
              label: this.getFormattedAddress(prediction),
              value: prediction.place_id
            }));
          } else {
            this.autocompleteOptions = [];
          }
        }
      );
    } else {
      this.autocompleteOptions = [];
    }
  }
 

  getFormattedAddress(prediction: any): string {
   let res = "";
   for(let i = 0; i < prediction.types.length; i++) { 
        res += prediction.terms[i].value + " "; 
    }
    return res;
  }
}

