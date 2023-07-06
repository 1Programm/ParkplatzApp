import { Component, OnInit } from '@angular/core';
import { LuxDialogRef } from '@ihk-gfi/lux-components';
import { ParkhausEditierenDto } from 'src/app/facade/dto/ParkhausEditierenDto';
import { ParkhausParkflaecheDto } from 'src/app/facade/dto/ParkhausParkflaecheDto';

@Component({
  selector: 'app-edit-parkhaus-dialog',
  templateUrl: './edit-parkhaus-dialog.component.html',
  styleUrls: ['./edit-parkhaus-dialog.component.scss']
})


export class EditParkhausDialogComponent implements OnInit {
  public parkhaus: ParkhausEditierenDto;
  constructor(public luxDialogRef: LuxDialogRef) { }

  ngOnInit(): void {
    if(this.luxDialogRef.data != null) {
      this.parkhaus = this.luxDialogRef.data;
    }

  }




}
