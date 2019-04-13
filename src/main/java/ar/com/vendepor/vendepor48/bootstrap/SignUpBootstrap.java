package ar.com.vendepor.vendepor48.bootstrap;

import ar.com.vendepor.vendepor48.domain.Client;
import ar.com.vendepor.vendepor48.domain.Publication;
import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.domain.security.SignUpClient;
import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.security.SignUpTokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SignUpBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final SignUpTokenService signUpTokenService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public SignUpBootstrap(SignUpTokenService signUpTokenService, ClientService clientService, PasswordEncoder passwordEncoder) {
        this.signUpTokenService = signUpTokenService;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        String token = UUID.randomUUID().toString();

        SignUpClient signUpClient = new SignUpClient(
                "cliente1@email.com",
                passwordEncoder.encode("123456"));

        SignUpToken signUpToken = new SignUpToken(token);
        signUpToken.setSignUpClient(signUpClient);

        signUpTokenService.save(signUpToken);

        SignUpToken signUpTokenSaved = signUpTokenService.findById(signUpToken.getId());

        List<Client> clients = new ArrayList<>();

        clients.add(getClient1(signUpTokenSaved));
        clients.add(getClient2());

        clientService.saveAll(clients);
    }

    private Client getClient1(SignUpToken signUpTokenSaved) {

        Client client1 = new Client();

        client1.setEmail(signUpTokenSaved.getSignUpClient().getEmail());
        client1.setPassword(signUpTokenSaved.getSignUpClient().getPassword());
        client1.setUsername("username1");
        client1.setFirstName("fName1");
        client1.setLastName("lName1");

        client1.addPublication(getPublication1());
        client1.addPublication(getPublication2());

        return client1;
    }

    private Client getClient2() {

        Client client2 = new Client();

        client2.setEmail("cliente2@email.com");
        client2.setPassword(passwordEncoder.encode("123456"));
        client2.setUsername("username2");
        client2.setFirstName("fName2");
        client2.setLastName("lName2");

        client2.addPublication(getPublication3());

        return client2;
    }

    private Publication getPublication1() {

        Publication publication = new Publication();

        publication.setTitle("Notebook Lenovo Celeron N4000 4gb 500gb Windows 10 81d0000r");
        publication.setDescription("LENOVO\n" +
                "\n" +
                "Notebook Lenovo Ideapad Intel Celeron 4gb 500gb Windows 10 !\n" +
                "\n" +
                "NOTEBOOK 14\" LENOVO IDEAPAD 330-14IGM WINDOWS 10 HOME\n" +
                "Intel Celeron N4000 / 4GB RAM / 500GB Almacenamiento\n" +
                "81D0000RAR\n" +
                "\n" +
                "Optimiza tu día.\n" +
                "\n" +
                "• DISEÑADO PARA ESTAR AL DÍA CONTIGO\n" +
                "Diseñado con un acabado protector final que lo protege del desgaste, y con detalles de goma en la parte inferior de la cubierta para maximizar la ventilación y prolongar la vida útil de los componentes.\n" +
                "\n" +
                "• ACELERA TU PRODUCTIVIDAD AHORA Y MAÑANA\n" +
                "Los innovadores procesadores Intel® Core™ y memoria RAM DDR4 aseguran la realización de tareas múltiples sin problemas, arranques rápidos y entretenimiento tipo cine en casa.\n" +
                "\n" +
                "• RAPID CHARGE, EN CUALQUIER MOMENTO\n" +
                "¿Se agota la batería y no tienes mucho tiempo? Cuenta con hasta 6 horas de autonomía y es compatible con el sistema Rapid Charge: 15 minutos de carga para 2 horas de uso.\n" +
                "\n" +
                "• WINDOWS 10 MEJORA CADA DÍA\n" +
                "Combina lo mejor con Windows 10. Compartir es más rápido que nunca, con ajustes intuitivos que facilitan la conexión instantánea con las personas que más te importan.\n" +
                "\n" +
                "• SONIDO CÁLIDO Y CLARIDAD REAL\n" +
                "Con una resolución HD, ofrece efectos visuales potentes mientras te desplazas. Combinado con Dolby Audio, brinda una experiencia de entretenimiento radicalmente mejorada.");

        publication.setAmount(18258.9);
        publication.setStartDateTime(LocalDateTime.now().plusHours(1));

        return publication;
    }

    public Publication getPublication2() {

        Publication publication = new Publication();

        publication.setTitle("Gta V Ps4 Fisico Grand Theft Auto V Gta5 Nuevo Sellado");
        publication.setDescription("Gta V Ps4 Fisico Grand Theft Auto V Gta5 Nuevo Sellado\n" +
                "\n" +
                "\n" +
                "– Idioma: Multi-Español\n" +
                "– Peso: 42 GB\n" +
                "– Trofeos: SI\n" +
                "– Online: SI\n" +
                "\n" +
                "\n" +
                "PRODUCTO ORIGINAL\n" +
                "Incluye todas las funciones y modos de juego.\n" +
                "\n" +
                "\n" +
                "##################################################\n" +
                "GAMING CITY COMPUTACION\n" +
                "##################################################\n" +
                "\n" +
                "Consulta por el stock antes de ofertar. Gracias! \n" +
                "\n" +
                "##################################################\n" +
                "\n" +
                "TENER EN CUENTA ANTES DE OFERTAR\n" +
                "\n" +
                "(1) . Despeje todas sus dudas con el sistema de preguntas al vendedor antes de ofertar.\n" +
                "(2) . Hacemos factura A y B.\n" +
                "(3) . Todos los precios son finales IVA incluido.\n" +
                "(4) . Contamos con un stock permanente de productos para envío a todo el país, pero no olvide consultar stock.\n" +
                "(5) . Disponemos de un local comercial con atención al público en Buenos Aires, Morón donde podes ver nuestros productos antes de comprar.\n" +
                "(6) . En caso de retirar personalmente comuníquese con nosotros a fin de coordinar el retiro de su compra.\n" +
                "(7) . Todos nuestro artículos son nuevos y se entregan en su empaque original.\n" +
                "(8) . No aceptamos ningún producto en parte de pago.");

        publication.setAmount(1699d);

        publication.setStartDateTime(LocalDateTime.now().minusHours(1));

        PublicationMessage message1 = new PublicationMessage("Message 1", LocalDateTime.now());
        PublicationMessage message2 = new PublicationMessage("Message 2", LocalDateTime.now());

        publication.addPublicationMessage(message1);
        publication.addPublicationMessage(message2);

        return publication;
    }

    public Publication getPublication3() {

        Publication publication = new Publication();

        publication.setTitle("Colchón Piero Sonno 1 Plaza 190x80 Resortes Ultra Coil");
        publication.setDescription("COLCHÓN PIERO SONNO 190x80 - 1 PLAZA\n" +
                "Medida: 190x80x26 cms.\n" +
                "Estructura de resortes continuos entrelazados (UltraCoil System).\n" +
                "Doble marco perimetral de acero y estabilizadores de acero reforzando la estructura.\n" +
                "Recomendado para personas de hasta 80 kgs aproximadamente. \n" +
                "Tapizado en tela jackard totalmente matelaseada.");

        publication.setAmount(6605d);

        publication.setStartDateTime(LocalDateTime.now());

        return publication;
    }
}
