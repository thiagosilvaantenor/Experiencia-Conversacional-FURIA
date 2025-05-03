import ChatForm from '../components/ChatForm';
import Image from 'next/image';

export const metadata = {
  title: 'ChatFuria',
}

export default function ChatPage() {
  return (
    <main className="p-5 ">
      <div className={"flex flex-col items-center mb-4"}>
        <Image src={"/furia-logo.png"} alt="Logo da FURIA" width={100} height={300} />
      </div>
      <ChatForm />
    </main>
  );
}
