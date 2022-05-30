import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'employmentType'
})
export class EmploymentTypePipe implements PipeTransform {

  transform(value: string): string {
    if (value == "FULL_TIME")
      return "Full time"
    if (value == "PART_TIME")
      return "Part time"
    if (value == "SELF_EMPLOYED")
      return "Self employed"
    if (value == "FREELANCE")
      return "Freelance"
    if (value == "CONTRACT")
      return "Contract"
    if (value == "INTERNSHIP")
      return "Internship"
    if (value == "APPRENTICESHIP")
      return "Apprenticeship"
    if (value == "SEASONAL")
      return "Seasonal"
    return "Undefined employment type"
  }

}
