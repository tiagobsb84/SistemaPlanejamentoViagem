import { FormEvent, useState } from "react"
import { useNavigate } from "react-router-dom";
import { InviteGuestsModal } from "./invite-guests-modal";
import { ConfirmTripModal } from "./confirm-trip-modal";
import { DestinationAndDateStep } from "./steps/destination-and-date-step";
import { InviteGuestsStep } from "./steps/invite-guests-step";

export function CreateTripPage() {

  const navigate = useNavigate();

  const [ isGuestsInputOpen, setIsGuestsInputOpen ] = useState(false);
  const [ isGuestsModalOpen, setIsGuestsModalOpen ] = useState(false);
  const [ emailsToInvite, setEmailsToInvite ] = useState([
    'tiagobsb31@gmail.com',
    'mariabsb55@gmail.com'
  ]);
  const [ isConfirmTripModalOpen, setIsConfirmTripModal ] = useState(false);

  function openGuestsInput(){
    setIsGuestsInputOpen(true);
  }

  function closeGuestsInput() {
    setIsGuestsInputOpen(false);
  }

  function openGuestsModal() {
    setIsGuestsModalOpen(true);
  }

  function closeGuestsModal() {
    setIsGuestsModalOpen(false);
  }

  function closeConfirmTripModal() {
    setIsConfirmTripModal(false);
  }

  function openConfirmTripModal() {
    setIsConfirmTripModal(true);
  }

  function addNewEmailToInvite(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();  

    const data = new FormData(event.currentTarget);

    const email = data.get('email')?.toString();

    /* caso o usuario não preencha o input, então não fazer nada. */
    if(!email) {
      return
    }

    /* verifica se já existe o email salvo. */
    if(emailsToInvite.includes(email)) {
      return
    }

    setEmailsToInvite([
      ...emailsToInvite, email
    ])

    event.currentTarget.reset();
  }

  function removeEmailFromInvite(emailToRemove: String) {

    const newEmailList = emailsToInvite.filter(email => email !== emailToRemove);

    setEmailsToInvite(newEmailList);
  }

  function createTrip(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    navigate('/trips/123');
  }

  return (
    <div className="h-screen flex items-center justify-center bg-pattern bg-no-repeat bg-center">
      <div className="w-3xl px-6 text-center space-y-10">
        <div className="flex flex-col items-center gap-3">
          <img src="/logo.svg" alt="logo da empresa" />
          <p className="text-zinc-300 text-lg">Convide seus amigos e planeje sua próxima viagem!</p>
        </div>
        
        <div className="space-y-4">
          <DestinationAndDateStep 
            closeGuestsInput={closeGuestsInput}
            isGuestsInputOpen={isGuestsInputOpen}
            openGuestsInput={openGuestsInput}
          />

          {isGuestsInputOpen && (
            <InviteGuestsStep 
              emailsToInvite={emailsToInvite}
              openGuestsModal={openGuestsModal}
              openConfirmTripModal={openConfirmTripModal}
            />
          )} 
        </div>

        <p className="text-sm text-zinc-500">
          Ao planejar sua viagem você automaticamente concorda <br />
          com nossos <a className="text-zinc-300 underline" href="#">termos de uso</a> e 
          <a className="text-zinc-300 underline" href="#">politicas de privacidade</a>
        </p>
      </div>

      {isGuestsModalOpen && (
        <InviteGuestsModal 
          closeGuestsModal={closeGuestsModal}
          addNewEmailToInvite={addNewEmailToInvite}
          emailsToInvite={emailsToInvite}
          removeEmailFromInvite={removeEmailFromInvite}
        />
      )}

      {isConfirmTripModalOpen && (
        <ConfirmTripModal
          closeConfirmTripModal={closeConfirmTripModal}
          createTrip={createTrip}
        />
      )}

    </div>
  )
}
