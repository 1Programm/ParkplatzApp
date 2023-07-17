import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DateUtils } from 'src/app/utils/date.utils';

@Component({
  selector: 'app-range-date-picker',
  templateUrl: './range-date-picker.component.html',
  styleUrls: ['./range-date-picker.component.scss']
})
export class RangeDatePickerComponent implements OnInit {

  @Input()
  public dateStart: string;

  @Input()
  public dateEnd: string;

  @Output()
  public dateStartChanged: EventEmitter<string> = new EventEmitter<string>();
  
  @Output()
  public dateEndChanged: EventEmitter<string> = new EventEmitter<string>();



  private _dateStartMin: string;
  private _dateStartMinDate: Date;

  @Input()
  public set dateStartMin(v: string){
    this._dateStartMin = v;
    this._dateStartMinDate = new Date(v);
  }

  public get dateStartMin() { 
    return this._dateStartMin;
  }


  private _dateStartMax: string;
  private _dateStartMaxDate: Date;

  @Input()
  public set dateStartMax(v: string){
    this._dateStartMax = v;
    this._dateStartMaxDate = new Date(v);
  }

  public get dateStartMax() { 
    return this._dateStartMax;
  }


  private _dateEndMin: string;
  private _dateEndMinDate: Date;

  @Input()
  public set dateEndMin(v: string){
    this._dateEndMin = v;
    this._dateEndMinDate = new Date(v);
  }

  public get dateEndMin() { 
    return this._dateEndMin;
  }


  private _dateEndMax: string;
  private _dateEndMaxDate: Date;

  @Input()
  public set dateEndMax(v: string){
    this._dateEndMax = v;
    this._dateEndMaxDate = new Date(v);
  }

  public get dateEndMax() { 
    return this._dateEndMax;
  }





  public get calculated_dateStartMin(): string {
    return this.dateStartMin;
  }

  public get calculated_dateStartMax(): string {
    if(this.dateStartMax && this.dateEnd){
      let diff = new Date(this.dateEnd).getTime() - this._dateStartMaxDate.getTime();
      let diffInDays = diff / (1000 * 3600 * 24);
      
      if(diffInDays < 0){
        return this.dateEnd;
      }
    }
    else if(this.dateEnd){
      return this.dateEnd;
    }

    return this.dateStartMax;
  }

  public get calculated_dateEndMin(): string {
    if(this.dateEndMin && this.dateStart){
      let diff = new Date(this.dateStart).getTime() - this._dateEndMinDate.getTime();
      let diffInDays = diff / (1000 * 3600 * 24);
      
      if(diffInDays < 0){
        return this.dateStart;
      }
    }
    else if(this.dateStart){
      return this.dateStart;
    }

    return this.dateEndMin;
  }

  public get calculated_dateEndMax(): string {
    return this.dateEndMax;
  }


  @Input()
  public disabled: boolean;

  constructor() { }

  ngOnInit(): void {
  }

  public onDateStartChange(date){
    this.dateStart = date;
    this.dateStartChanged.emit(date);
  }

  public onDateEndChange(date){
    this.dateEnd = date;
    this.dateEndChanged.emit(date);
  }

}
