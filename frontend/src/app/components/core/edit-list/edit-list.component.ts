import { Component, EventEmitter, Input, IterableDiffer, IterableDiffers, Output } from '@angular/core';

class EditListAttrib {
  public name: string;
  public typ: any;
  public choices?: any[];
  public label: string;
  public value: any;
  public errorMessage: string | undefined;
}

export class AttribInfo {
  public name: string;
  public typ?: any;
  public label?: string;
  public validator?: AttributeValidator;
}

export type AttributeValidator = (any) => string | undefined;

@Component({
  selector: 'app-edit-list',
  templateUrl: './edit-list.component.html',
  styleUrls: ['./edit-list.component.scss']
})
export class EditListComponent {
  
  //The item array
  private _items: any[];

  //The stored visible attributes for every item
  //Will be automatically updated every time the item array changes
  public itemAttribs: EditListAttrib[][];

  @Input()
  public set items(items: any[]){
    this._items = items;
    this.generateAttribsToItems();
  }

  public get items(){
    return this._items;
  }

  //Diff object to detect changes in the item array
  private itemsIterableDif: IterableDiffer<any[]>;

  //The visible attributes as a string array
  //You can input either a basic string array which describes the attribute names of the items which should be visible
  //Or you can pass an AttribInfo array (see AttribInfo in this file)
  private _attributes: AttribInfo[];

  @Input()
  public set attributes(attributes: string[] | AttribInfo[]){
    this._attributes = [];
    for(let attrib of attributes){
      if(typeof(attrib) === 'string'){
        let label = attrib.substring(0, 1).toUpperCase() + attrib.substring(1);
        this._attributes.push({name: attrib, typ: "string", label, validator: undefined});
      }
      else {
        let info = new AttribInfo();
        info.name = attrib.name;
        info.typ = attrib.typ;
        info.validator = attrib.validator;

        if(attrib.label) {
          info.label = attrib.label;
        }
        else {
          info.label = attrib.name.substring(0, 1).toUpperCase() + attrib.name.substring(1);
        }
        

        if(typeof(attrib.typ) === "object"){
            info["choices"] = attrib.typ;
            info.typ = "choices";
        }
        else if(attrib.typ === undefined){
          info.typ = "string";
        }

        this._attributes.push(info);
      }

      // console.log(attrib, this._attributes);
    }
  }

  public get attributes(): AttribInfo[]{
    return this._attributes;
  }



  //If true this class will use the methods 'push' and 'splice' to add an delete items from the given item array
  //If you want to do this yourself set this to false and use the onDelete and onAdd Outputs
  @Input()
  public doAutoChange: boolean = true;

  //Disable / Enable the delete button for each item
  @Input()
  public canDelete: boolean = false;

  //Disable / Enable the 'Add Item' button
  @Input()
  public canAdd: boolean = false;

  //Disable / Enable the readonly property of the input fields so a row can be edited
  @Input()
  public canEdit: boolean = false;

  //A callback Function which will be called to disable / enable the delete button
  //Can be used to disable the delete button for some specific items
  @Input()
  public canDeleteFn: (any) => boolean;
  
  //The EventEmitter for the delete-item-event 
  @Output()
  public onDelete: EventEmitter<number> = new EventEmitter<number>();

  //The EventEmitter for the add-item-event
  @Output()
  public onAdd: EventEmitter<any> = new EventEmitter<any>();

  //The EventEmitter for the edit-item-event
  @Output()
  public onEdit: EventEmitter<{name: string, value: any}> = new EventEmitter<{name: string, value: any}>();

  //If true a row for the new item will be shown
  public showNewItemRow: boolean = false;

  //The bound data list for the new item
  public newItemAttribs: EditListAttrib[];

  //Enables / Disables the 'Save' button for saving the new item row.
  //Is being controlled by the validators in the attributes array.
  public newItemValid: boolean;

  
  constructor(iterableDiffers: IterableDiffers) {
    this.itemsIterableDif = iterableDiffers.find([]).create(null);
  }

  ngOnInit(){
    this.generateAttribsToItems();
  }

  ngDoCheck() {
      let changes = this.itemsIterableDif.diff(this._items);
      if (changes) {
          this.generateAttribsToItems();
      }
  }

  private generateAttribsToItems(){
    this.itemAttribs = [];
    if(this._items) {
      for(let item of this._items){
        let attribs = this.getAttributes(item);
        this.itemAttribs.push(attribs);
      }
      console.log(this._attributes);
      
    }
  }

  private getAttributes(item): EditListAttrib[] {
    let attrs: EditListAttrib[] = [];

    if(this.attributes){
      for(let i=0;i<this.attributes.length;i++){
        let attrib = this.attributes[i];

        let value = item[attrib.name];

        attrs.push({name: attrib.name, typ: attrib.typ, choices: attrib["choices"], label: attrib.label, value, errorMessage: undefined});
      }
    }

    return attrs;
  }

  public onAttribChange(itemIndex: number, attribIndex: number, value: string){
    let attrib = this.itemAttribs[itemIndex][attribIndex];
    let attribName = attrib.name;

    let theAttribInfo: AttribInfo = undefined;
    for(let attribInfo of this.attributes){
      if(attribInfo.name === attribName){
        theAttribInfo = attribInfo;
        break;
      }
    }

    if(theAttribInfo.validator) {
      let message = theAttribInfo.validator(value);

      if(message){
        attrib.errorMessage = message;
        return;
      }
    }

    this.onEdit.emit({name: attribName, value});

    if(attrib.errorMessage) attrib.errorMessage = undefined;

    this.items[itemIndex][attribName] = value;
    attrib.value = value;
  }

  public onNewItemAttribChange(attribIndex: number, value: string){
    let attrib = this.newItemAttribs[attribIndex];
    let attribName = attrib.name;

    let theAttribInfo: AttribInfo = undefined;
    for(let attribInfo of this.attributes){
      if(attribInfo.name === attribName){
        theAttribInfo = attribInfo;
        break;
      }
    }

    if(attrib.errorMessage) attrib.errorMessage = undefined;

    if(theAttribInfo.validator) {
      let message = theAttribInfo.validator(value);

      if(message){
        attrib.errorMessage = message;
      }
    }

    this.newItemAttribs[attribIndex].value = value;

    let valid = true;
    for(let attrib of this.newItemAttribs){
      if(attrib.errorMessage) {
        valid = false;
        break;
      }
    }

    this.newItemValid = valid;
  }

  public checkCanDelete(item){
    if(this.canDelete === false) return false;
    return this.canDeleteFn === undefined || this.canDeleteFn(item);
  }

  public actionDeleteItem(index) {
    if(this.doAutoChange) {
      this._items.splice(index, 1);
    }

    this.onDelete.emit(this._items[index]);
  }
  
  public actionAddItem(){
    this.showNewItemRow = true;
    this.newItemValid = false;
    this.newItemAttribs = [];
    for(let attrib of this.attributes){
      this.newItemAttribs.push({name: attrib.name, typ: attrib.typ, choices: attrib["choices"], label: attrib.label, value: "", errorMessage: "This field is required!"});
    }
  }

  public actionSaveNewItem(){
    this.showNewItemRow = false;

    let newItem = {};
    for(let attrib of this.newItemAttribs){
      newItem[attrib.name] = attrib.value;
    }

    if(this.doAutoChange) {
      this._items.push(newItem);
    }

    this.onAdd.emit(newItem);
  }

}
