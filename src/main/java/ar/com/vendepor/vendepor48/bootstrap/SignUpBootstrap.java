package ar.com.vendepor.vendepor48.bootstrap;

import ar.com.vendepor.vendepor48.domain.*;
import ar.com.vendepor.vendepor48.domain.security.SignUpClient;
import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.PublicationService;
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
    private final PublicationService publicationService;
    private final PasswordEncoder passwordEncoder;

    public SignUpBootstrap(SignUpTokenService signUpTokenService, ClientService clientService, PublicationService publicationService, PasswordEncoder passwordEncoder) {
        this.signUpTokenService = signUpTokenService;
        this.clientService = clientService;
        this.publicationService = publicationService;
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

        Publication publication1 = getPublication1();
        Publication publication2 = getPublication2();
        Publication publication3 = getPublication3();

        PublicationMessage message1 = new PublicationMessage("Message 1", LocalDateTime.now().plusSeconds(1));
        PublicationMessage message2 = new PublicationMessage("Message 2", LocalDateTime.now().plusSeconds(2), true);
        PublicationMessage message3 = new PublicationMessage("Message 3", LocalDateTime.now().plusSeconds(3));
        PublicationMessage message4 = new PublicationMessage("Message 4", LocalDateTime.now().plusSeconds(4));
        PublicationMessage message5 = new PublicationMessage("Message 5", LocalDateTime.now().plusSeconds(5));
        PublicationMessage message6 = new PublicationMessage("Message 6", LocalDateTime.now().plusSeconds(6));

        publication1.addPublicationMessage(message1);
        publication1.addPublicationMessage(message2);
        publication1.addPublicationMessage(message4);
        publication1.addPublicationMessage(message5);
        publication1.addPublicationMessage(message6);

        Client client1 = getClient1(signUpTokenSaved);
        Client client2 = getClient2();

        client1.addPublication(publication1);
        client1.addPublication(publication3);
        client2.addPublication(publication2);

        message1.setClient(client2);
        message2.setClient(client2);
        message3.setClient(client2);
        message4.setClient(client2);
        message5.setClient(client2);
        message6.setClient(client2);

        clients.add(client1);
        clients.add(client2);

        clientService.saveAll(clients);

        publication1.addPublicationMessage(message3);

        publicationService.save(publication1);
    }

    private Client getClient1(SignUpToken signUpTokenSaved) {

        Client client1 = new Client();

        client1.setEmail(signUpTokenSaved.getSignUpClient().getEmail());
        client1.setPassword(signUpTokenSaved.getSignUpClient().getPassword());
        client1.setUsername("username1");
        client1.setFirstName("fName1");
        client1.setLastName("lName1");

        return client1;
    }

    private Client getClient2() {

        Client client2 = new Client();

        client2.setEmail("cliente2@email.com");
        client2.setPassword(passwordEncoder.encode("123456"));
        client2.setUsername("username2");
        client2.setFirstName("fName2");
        client2.setLastName("lName2");

        return client2;
    }

    private Publication getPublication1() {

        Publication publication = new Publication();

        publication.setTitle("Cena con entrada para compartir + platos de fondo + postres + copa de vino para 2 personas");
        publication.setDescription("Entrada para compartir \n" +
                "\n" +
                "Buñuelos Galpón \n" +
                "Buñuelos de espinaca y corazón de gruyere con alioli de azafrán \n" +
                "Won ton: típicas empanadillas asiáticas de pollo orgánico, sésamo tostado y verdeo con salsa teriyaki \n" +
                "Platos por persona \n" +
                "\n" +
                "Shepherd´s Pie: plato tradicional británico a base de carne de cordero, vegetales y topping de puré de papa\n" +
                "Ragout de ternera, osobuco y hongos: estofado de carnes, osobuco, vegetales y hongos con crema agria \n" +
                "Cerdo desmechado: carne de cerdo horneada en fondo de cocción con batatas, berenjenas y chutney de estación \n" +
                "Pollo tikka masala: preparación de origen Indo-Pakistani de pollo macerado en yogurth, especias orientales con arroz blanco y pan naan de ajo \n" +
                "Abadejo al horno: lomo de abadejo al horno con cascara de cítricos y salsa thai tibia\n" +
                "Lasagna de vegetales: lasagna abierta de vegetales sarteneados, salsa filetto y manteca de limón \n" +
                "Pappardelle rigate: pasta tricolor con salsa de: pesto, filetto o zuchinnis asados \n" +
                "Postres por persona:\n" +
                "\n" +
                "Volean de chocolate con almendras y ralladura de cítricos \n" +
                "Mousse de queso, espuma de vino y reducción ácida \n" +
                "Meregue tibio de crema y frutos rojos \n" +
                "Crumble de manzana ,frutas asadas y helado de limón \n" +
                "Copa de vino por persona ");

        publication.setAmount(899d);
        publication.setStartDateTime(LocalDateTime.now().plusHours(1));
        publication.setSold(false);

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
        publication.setSold(false);

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
        publication.setSold(false);

        return publication;
    }
}
