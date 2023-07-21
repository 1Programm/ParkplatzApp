import { VerstossStatusDto } from "./VerstossStatus.dto"

export interface VerstossDto {
    meldeID: number
    datum: Date
    bemerkung: string
    status: VerstossStatusDto
}