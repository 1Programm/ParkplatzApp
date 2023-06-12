import { TemplateRef } from "@angular/core";
import { ILuxDialogAction, ILuxDialogPresetConfig } from "@ihk-gfi/lux-components";
import { Observable, Subject } from "rxjs";

export class DialogConfigFactory {

    private dialogConfig: ILuxDialogPresetConfig;

    public constructor() {
        this.dialogConfig = {
            height: 'auto',
            width: 'auto',
            disableClose: true,
            panelClass: [],
            confirmAction: {
                label: 'Okay',
                raised: true,
                color: 'warn'
              },
              declineAction: {
                label: 'Abbrechen',
                raised: true
              }
        };
    }

    setConfirmAction(action: ILuxDialogAction): DialogConfigFactory {
        this.dialogConfig.confirmAction = action;
        return this;
    }

    setTitle(title: string): DialogConfigFactory {
        this.dialogConfig.title = title;
        return this;
    }

    setHeight(height: string): DialogConfigFactory {
        this.dialogConfig.height = height;
        return this;
    }

    setWidth(width: string): DialogConfigFactory {
        this.dialogConfig.width = width;
        return this;
    }

    setDeclineAction(action: ILuxDialogAction): DialogConfigFactory {
        this.dialogConfig.declineAction = action;
        return this;
    }
    setContentTemplate(template: TemplateRef<any>): DialogConfigFactory {
        this.dialogConfig.contentTemplate = template
        return this;
    }
    setContent(content: string): DialogConfigFactory {
        this.dialogConfig.content = content;
        return this;
    }

    public build(): ILuxDialogPresetConfig {
        return this.dialogConfig;
    }
}

