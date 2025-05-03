'use client';
import ChatForm from "./ChatForm";
import Image from 'next/image';

export default function ChatPage() {
  return(
    <main className="p-5">
        <h1 className="text-xl text-center font-bold mb-4">
            Bem-vindo ao Chat Bot
            <Image src={"/furia-logo.png"} alt="Logo da FURIA" width={55} height={32} />
        </h1>
        <ChatForm/>
    </main>
  );
}
