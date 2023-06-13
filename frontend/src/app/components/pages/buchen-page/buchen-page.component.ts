import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import {ParkflaecheAuswahlDto } from '../../../dtos/parkflaeche-auswahl.dto';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  parkanlagen : ParkflaecheAuswahlDto[];
  selectedParkanlage: ParkflaecheAuswahlDto;

  constructor(private buchungService: BuchungService) {}

  ngOnInit(): void {
    this.getParkAnlagen();

    setTimeout(() => {
      console.log("Test add item after time in parent component!");
      this.testData.push({name: "Arnold", age: 100});
    }, 2000);

    setTimeout(() => {
      console.log("++++", this.testData[0].name);
    }, 5000);
  }

  startDatum: string = new Date().toLocaleDateString();

  public getParkAnlagen() : ParkflaecheAuswahlDto[] {
    this.buchungService.getParkanlagen().subscribe(parkanlagen => {
      this.parkanlagen = parkanlagen;
      this.selectedParkanlage = parkanlagen[0];
    });
    return this.parkanlagen;
  }


  public testData = [
    {name: "Julian", age: 23},
    {name: "Otto", age: 3},
    {name: "Max", age: 99}
  ];

  public testAttribs = [
    //Validator inline
    {name: 'name', validator: (value) => {
      return value.includes(' ') ? "You cannot have spaces in the name!" : undefined;
    }},
    //Validator as a method
    {name: 'age', validator: this.ageValidator}
  ]



  public onAdd(item: any){
    console.log("Added new item: ", item);
  }

  public onDelete(pos: number){
    console.log("Deleted Index: ", pos);
  }

  private ageValidator(age: string){
    if(age.match(/\%^[0-9]+\%$/)) return undefined;
    if((+age) > 0 && (+age) <= 99) return undefined;
    return "The age must be a number from 1-99!";
  }
}
