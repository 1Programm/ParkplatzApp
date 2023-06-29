export class DateUtils {

    public static toString(date: Date): string {
        return date.toISOString();
    }

    public static getToday(): Date {
        return new Date();
    }

    public static getTodayAsString(): string {
        return this.toString(this.getToday());
    }

    public static getFuture(daysIntoTheFuture: number): Date {
        let date = this.getToday();
        date.setDate(date.getDate() + daysIntoTheFuture);
        return date;
    }

    public static getFutureAsString(daysIntoTheFuture: number): string {
        return this.toString(this.getFuture(daysIntoTheFuture));
    }

    public static getFuture_2Weeks(): Date {
        return this.getFuture(14);
    }

    public static getFuture_2WeeksAsString(): string {
        return this.toString(this.getFuture_2Weeks());
    }

}