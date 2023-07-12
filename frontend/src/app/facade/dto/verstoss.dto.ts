import { VerstossStatus } from "src/app/utils/verstossStatus.enum"

export interface VerstossDto {
    meldeID: number
    mitarbeiterID: number
    datum: Date
    bemerkung: string
    status: VerstossStatus
}