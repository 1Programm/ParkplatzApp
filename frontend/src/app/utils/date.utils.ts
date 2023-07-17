import { formatDate } from "@angular/common";

export class DateUtils {

    public static toTechnicalString(date: Date): string {
        return date.toISOString();
    }

    public static toVisibleString(date: Date): string {
        return formatDate(date, 'dd/MM/YYYY', "de-DE");
    }

    public static removeTimeFromDate(date: Date): void {
        date.setHours(0, 0, 0, 0);
    }

    public static getToday(): Date {
        return new Date();
    }

    public static getTodayAsString(): string {
        return this.toTechnicalString(this.getToday());
    }

    public static getFuture(daysIntoTheFuture: number): Date {
        let date = this.getToday();
        date.setDate(date.getDate() + daysIntoTheFuture);
        return date;
    }

    public static getFutureAsString(daysIntoTheFuture: number): string {
        return this.toTechnicalString(this.getFuture(daysIntoTheFuture));
    }

    public static getFuture_2Weeks(): Date {
        return this.getFuture(14);
    }

    public static getFuture_2WeeksAsString(): string {
        return this.toTechnicalString(this.getFuture_2Weeks());
    }

}