package heartMonitor.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomProtocol implements Serializable {
    private static final long serialVersionUID = 4671171056588401542L;
    private long id ;
    private String content ;
}