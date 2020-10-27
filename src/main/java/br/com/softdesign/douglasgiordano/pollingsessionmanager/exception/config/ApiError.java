package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

   private HttpStatus status;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
   private LocalDateTime timestamp;
   private String message;
   private String debugMessage;

   private ApiError() {
       timestamp = LocalDateTime.now();
   }

    public ApiError(HttpStatus status) {
       this();
       this.status = status;
   }

   public ApiError(HttpStatus status, Throwable ex) {
       this();
       this.status = status;
       this.message = "Unexpected error";
       this.debugMessage = ex.getLocalizedMessage();
   }

    public ApiError(HttpStatus status, String message, Throwable ex) {
       this();
       this.status = status;
       this.message = message;
       this.debugMessage = ex.getLocalizedMessage();
   }
}