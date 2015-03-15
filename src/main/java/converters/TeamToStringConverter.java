
package converters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.Team;
@Component
@Transactional
public class TeamToStringConverter implements Converter<Team, String> {
@Override
public String convert(Team arg0) {
return String.valueOf(arg0.getId());
}
}
